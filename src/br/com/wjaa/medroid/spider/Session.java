package br.com.wjaa.medroid.spider;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;

import br.com.wjaa.medroid.spider.exception.SessionException;
import br.com.wjaa.medroid.spider.vo.Categoria;
import br.com.wjaa.medroid.spider.vo.Conta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Wagner Araujo wagner.wjaa@gmail.com
 *
 */
class Session {
	
	private static final int MAX_MINUTE_SESSION = 29;
	private static final String HEADER_CACHE_CONTROL_VALUE = "no-cache";
	private static final String HEADER_CACHE_CONTROL = "Cache-Control";
	private static final String HEADER_CONNECTION_VALUE = "keep-alive";
	private static final String HEADER_CONNECTION = "Connection";
	private static final String PARAM_ACTION_INIT = "{\"action\":\"init\"}";
	private static final String HEADER_CONTENT_TYPE_VALUE_JSON = "application/json; charset=UTF-8";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String HEADER_REFERER_VALUE_PRINCIPAL = "https://www.minhaseconomias.com.br/principal.do";
	private static final String HEADER_X_REQUESTED_WITH_VALUE = "XMLHttpRequest";
	private static final String HEADER_X_REQUESTED_WITH = "X-Requested-With";
	private static final String HEADER_ACCEPT_VALUE_TEXT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private static final String HEADER_ACCEPT_LANGUAGE_VALUE = "pt-br,pt;q=0.8,en-us;q=0.5,en;q=0.3";
	private static final String HEADER_REFERER_VALUE_HOME = "https://www.minhaseconomias.com.br/home.do";
	private static final String HEADER_PRAGMA_VALUE = HEADER_CACHE_CONTROL_VALUE;
	private static final String HEADER_HOST_VALUE = "wwws.minhaseconomias.com.br";
	private static final String HEADER_ACCEPT = "Accept";
	private static final String HEADER_ACCEPT_LANGUAGE = "Accept-Language";
	private static final String HEADER_REFERER = "Referer";
	private static final String HEADER_PRAGMA = "Pragma";
	private static final String HEADER_HOST = "Host";
	private static final String SESSION = "http://www.minhaseconomias.com.br/";
	private static final String LOGIN = "https://www.minhaseconomias.com.br/login";
	private static final String SUBMIT_LOGIN = "https://www.minhaseconomias.com.br/usuariologon.do";
	private static final String PRINCIPAL = HEADER_REFERER_VALUE_PRINCIPAL;
	private static final String DIRECT = "https://www.minhaseconomias.com.br/direct.do";
	
	private static final String PARAM_EMAIL = "email";
	private static final String PARAM_SENHA = "senha";
	private static SessionValidate sessionValidate;
	private Usuario usuario;
	
	private static final DefaultHttpClient client = new DefaultHttpClient();
	private static final Log LOG = LogFactory.getLog(Session.class);
	
	static{
		/*HttpHost proxy = new HttpHost("proxyfiliais.braspress.com.br", 3128);
		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		client.getCredentialsProvider().setCredentials(AuthScope.ANY,new UsernamePasswordCredentials("wagneraraujo-sao","sao1234"));*/
	}
	

	private Integer tid;
	private List<Categoria> categorias;
	private List<Conta> contas;
	private Integer clientid;
	
	
	public Session(String user, String pass) throws SessionException{
		this.usuario = new Usuario(user,pass);
		this.createSession(user, pass);
	}

	private void createSession(String user, String pass)
			throws SessionException {
		doSession();
		doSessionLogin();
		doSubmitLogin(user,pass);
		doPrincipal(user);
		this.clientid = doGetCliente();
		doGetStartAtributos(this.clientid);
		doGetAtributos(this.clientid);
		LOG.debug("Usuario autenticado com sucesso, SESSION CRIADA!!!");
		sessionValidate = new SessionValidate(new Date(), MAX_MINUTE_SESSION);
	}
	
	private void reloadSession() throws SessionException{
		System.out.println("Recarregando sessao....");
		this.createSession(usuario.getUser(), usuario.getPass());
	}
	
	public void inserirTransacao(Date data, String descricao, String idCategoria,String idConta, Double valor) throws SessionException{
		
		
		this.validate(data,descricao,idCategoria,idConta,valor);
		
		if (!sessionValidate.isSessionValid()){
			reloadSession();
		}
		
		if (tid == null){
			tid = 100;
		}else{
			tid ++;
		}
		
		Transacao senders [] = new Transacao[1];
		Transacao transacao = new Transacao();
		senders[0] = transacao;
		transacao.setTid(this.tid);
		transacao.setClientid(this.clientid);
		Dados dados = new Dados();
		dados.setCategoriaId(idCategoria);
		dados.setContaId(idConta);
		dados.setValor(valor);
		dados.setDescricao(descricao);
		dados.setDataTransacao(data);
		transacao.addData(dados);
		
		this.doSend(senders);
		
		sessionValidate.updateTimeSession(new Date());
	}
	
	
	private void validate(Date data, String descricao, String idCategoria,
			String idConta, Double valor) throws SessionException {
		String msg = "A {0} da transaÁ„o n„o foi preenchida.";
		if (data == null){
			throw new SessionException(msg.replace("{0}", "data"));
		}
		
		if (StringUtils.isEmpty(descricao)){
			throw new SessionException(msg.replace("{0}", "descriÁ„o"));
		}
		
		if (StringUtils.isEmpty(idCategoria) || !NumberUtils.isNumber(idCategoria)){
			throw new SessionException(msg.replace("{0}", "categoria"));
		}
		
		if (StringUtils.isEmpty(idConta) || !NumberUtils.isNumber(idConta)){
			throw new SessionException(msg.replace("{0}", "conta"));
		}
		
		if (valor == null || valor < 1.0){
			throw new SessionException("Valor da transaÁ„o tem que ser maior que zero.");
		}
		
	}

	private void doSession()throws SessionException {
		try{
			HttpGet sessionGet = new HttpGet(SESSION);
			HttpResponse response = client.execute(sessionGet);
			if (hasError(response)){
				throw new SessionException("Erro ao carregar a primeira pagina");
			}
			//sessionGet.releaseConnection();
			
		}catch(Exception ex){
			throw new SessionException("Erro ao carregar a primeira pagina", ex);
		}
	}
	
	private void doSessionLogin() throws SessionException{
			try{
				HttpGet loginGet = new HttpGet(LOGIN);
				HttpResponse response = client.execute(loginGet);
				if (hasError(response)){
					throw new SessionException("Erro ao acessar a pagina de login");
				}
				//loginGet.releaseConnection();
			}catch (Exception e) {
				throw new SessionException("Erro ao acessa a pagina de login",e);
			}
	}
	
	private void doSubmitLogin(String user, String pass) throws SessionException {
		try{
			HttpPost loginPost = new HttpPost(SUBMIT_LOGIN);
			loginPost.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			loginPost.setHeader(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
			loginPost.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_HOME);
			loginPost.setHeader(HEADER_X_REQUESTED_WITH, HEADER_X_REQUESTED_WITH_VALUE);
			loginPost.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			loginPost.setHeader(HEADER_ACCEPT,"application/json, text/javascript, */*; q=0.01");
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair(PARAM_EMAIL, user));
			postParams.add(new BasicNameValuePair(PARAM_SENHA, pass));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
			loginPost.setEntity(entity);
			HttpResponse response = client.execute(loginPost);
			
			if (hasError(response)){
				throw new SessionException("Erro ao autenticar o usu√°rio:\n " + getResponseBody(response).toString());
			}
			
			StringBuilder sb = getResponseBody(response);
			if (sb.toString().contains("success") && sb.toString().contains("false")){
				throw new SessionException("O retorno da autenticacao nao contem os dados [SUCCESS : TRUE]");
			}
			//loginPost.releaseConnection();
		}catch(Exception e){
			throw new SessionException("Erro no login", e);
		}
	}
	
	
	private void doPrincipal(String user) throws SessionException{

		try{
			
			client.getCookieStore().addCookie(new BasicClientCookie("me_usr", user));
			
			HttpGet principalGet = new HttpGet(PRINCIPAL);
			principalGet.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			principalGet.setHeader(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
			principalGet.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_HOME);
			principalGet.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			principalGet.setHeader(HEADER_ACCEPT,HEADER_ACCEPT_VALUE_TEXT_HTML);
			HttpResponse response = client.execute(principalGet);
			if (hasError(response)){
				throw new SessionException("Erro ao acessar a pagina principal: \n" + getResponseBody(response).toString());
			}
			
			StringBuilder sb = getResponseBody(response);
			if (!sb.toString().contains(user)){
				throw new SessionException("Pagina principal n√£o √© a esperada ela n√£o contem o nome de usu√°rio.");

			}
			//principalGet.releaseConnection();
		}catch(Exception ex){
			throw new SessionException("Erro ao acessar a pagina principal", ex);
		}
	}

	
	private Integer doGetCliente() throws SessionException{
		try{
			HttpPost clientPost = new HttpPost(DIRECT);
			clientPost.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			clientPost.setHeader(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
			clientPost.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_PRINCIPAL);
			clientPost.setHeader(HEADER_X_REQUESTED_WITH, HEADER_X_REQUESTED_WITH_VALUE);
			clientPost.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			clientPost.setHeader(HEADER_ACCEPT,HEADER_ACCEPT_VALUE_TEXT_HTML);
			clientPost.setHeader(HEADER_CONTENT_TYPE,HEADER_CONTENT_TYPE_VALUE_JSON);
			
			StringEntity sEntity = new StringEntity(PARAM_ACTION_INIT);
			clientPost.setEntity(sEntity);
			HttpResponse response = client.execute(clientPost);
			
			
			if (hasError(response)){
				throw new SessionException("Erro ao busca o clientid: \n" + getResponseBody(response).toString());
			}
			
			StringBuilder sb = getResponseBody(response);
			if (!sb.toString().contains("clientid")){
				throw new SessionException("O Retorno da requisicao nao retorno ou clientid: \n" + getResponseBody(response).toString());
			}
			
			//clientPost.releaseConnection();
			
			return Integer.valueOf(sb.toString().replaceAll("[^0-9]", ""));
		}catch(Exception ex){
			throw new SessionException("Erro ao busca o clientid", ex);
		}
		
		
	}
	
	
	private void doGetStartAtributos(Integer clientid) throws SessionException{
		String jsonPost = "[{\"tid\":2,\"data\":null,\"action\":\"queue\",\"method\":\"loadcontas\",\"clientid\":" + clientid +"}," +
						   "{\"tid\":3,\"data\":null,\"action\":\"queue\",\"method\":\"loadcategorias\",\"clientid\":" + clientid +"}]";
		
		try{
			HttpPost startCategoriasPost = new HttpPost(DIRECT);
			startCategoriasPost.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			startCategoriasPost.setHeader(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
			startCategoriasPost.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_PRINCIPAL);
			startCategoriasPost.setHeader(HEADER_X_REQUESTED_WITH, HEADER_X_REQUESTED_WITH_VALUE);
			startCategoriasPost.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			startCategoriasPost.setHeader(HEADER_ACCEPT,HEADER_ACCEPT_VALUE_TEXT_HTML);
			startCategoriasPost.setHeader(HEADER_CONTENT_TYPE,HEADER_CONTENT_TYPE_VALUE_JSON);
			
			StringEntity sEntity = new StringEntity(jsonPost);
			sEntity.setContentType(HEADER_CONTENT_TYPE_VALUE_JSON);
			startCategoriasPost.setEntity(sEntity);
			HttpResponse response = client.execute(startCategoriasPost);
			
			if (hasError(response)){
				throw new SessionException("Erro ao iniciar a busca dos atributos: \n" + getResponseBody(response).toString());
			}
			
			//startCategoriasPost.releaseConnection();
			
		}catch(Exception ex){
			throw new SessionException("Erro ao iniciar a busca dos atributos", ex);
		}
	
	}
	
	

	private void doGetAtributos(Integer clientid) throws SessionException{
		
		try{
			HttpPost categoriasPost = new HttpPost(DIRECT);
			categoriasPost.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			categoriasPost.setHeader(HEADER_PRAGMA, HEADER_PRAGMA_VALUE);
			categoriasPost.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_PRINCIPAL);
			categoriasPost.setHeader(HEADER_X_REQUESTED_WITH, HEADER_X_REQUESTED_WITH_VALUE);
			categoriasPost.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			categoriasPost.setHeader(HEADER_ACCEPT,HEADER_ACCEPT_VALUE_TEXT_HTML);
			categoriasPost.setHeader(HEADER_CONNECTION,HEADER_CONNECTION_VALUE);
			categoriasPost.setHeader(HEADER_CONTENT_TYPE,HEADER_CONTENT_TYPE_VALUE_JSON);
			categoriasPost.setHeader(HEADER_CACHE_CONTROL,HEADER_CACHE_CONTROL_VALUE);
			
			
			StringEntity sEntity = new StringEntity("{\"data\":[2,3],\"action\":\"query\",\"clientid\":" + clientid + "}");
			sEntity.setContentType(HEADER_CONTENT_TYPE_VALUE_JSON);
			categoriasPost.setEntity(sEntity);
			HttpResponse response = client.execute(categoriasPost);
			
			
			if (hasError(response)){
				throw new SessionException("Erro ao buscar os atributos: \n" + getResponseBody(response).toString());
			}
			
			StringBuilder sb = getResponseBody(response);
			if (!sb.toString().contains("data")){
				throw new SessionException("O retorno da requisicao nao retornou o campo data.\n" + getResponseBody(response).toString());
			}
			
			Gson gson = new GsonBuilder().create();
			List<Atributos> atributos = gson.fromJson(sb.toString(), new TypeToken<List<Atributos>>() {}.getType());
			convertAtributos(atributos);
			
			//categoriasPost.releaseConnection();
		}catch(Exception ex){
			throw new SessionException("Erro ao buscar os atributos", ex);
		}
		
	}

	private void convertAtributos(List<Atributos> atributos) {
		this.categorias = new ArrayList<Categoria>();
		this.contas = new ArrayList<Conta>();
		for (Atributos a : atributos) {
			for (Values v : a.getData()) {
				if (v.isCategoria()){
					Categoria c = new Categoria(v.getId(), v.getText());
					categorias.add(c);
				}else if (v.isConta()){
					Conta conta = new Conta(v.getId(), v.getNome());
					contas.add(conta);
				}
			}
		}
		
	}
	

	private void doSend(Transacao [] sender) throws SessionException{
		try{
			HttpPost sendPost = new HttpPost(DIRECT);
			sendPost.setHeader(HEADER_HOST, HEADER_HOST_VALUE);
			sendPost.setHeader(HEADER_PRAGMA, HEADER_CACHE_CONTROL_VALUE);
			sendPost.setHeader(HEADER_REFERER, HEADER_REFERER_VALUE_PRINCIPAL);
			sendPost.setHeader(HEADER_X_REQUESTED_WITH, HEADER_X_REQUESTED_WITH_VALUE);
			sendPost.setHeader(HEADER_ACCEPT_LANGUAGE,HEADER_ACCEPT_LANGUAGE_VALUE);
			sendPost.setHeader(HEADER_ACCEPT,HEADER_ACCEPT_VALUE_TEXT_HTML);
			sendPost.setHeader(HEADER_CONTENT_TYPE,	HEADER_CONTENT_TYPE_VALUE_JSON);
			Gson g = new GsonBuilder().create();
			String json = g.toJson(sender);
			StringEntity sEntity = new StringEntity(json);
			sendPost.setEntity(sEntity);
			HttpResponse response = client.execute(sendPost);
			
			StringBuilder sb = getResponseBody(response);
			//analisar o resultado final pra ter certeza que foi enviado
			System.out.println(sb.toString());
			
		}catch(Exception ex){
			throw new SessionException("Erro ao enviar uma transacao", ex);
		}
	}
	
	
	private static StringBuilder getResponseBody(HttpResponse response) throws IllegalStateException, IOException{
		return toStringBuilder(response.getEntity().getContent());
	}
	
	private static StringBuilder toStringBuilder(InputStream in) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(IOUtils.toCharArray(in,"iso-8859-1"));
		return sb;
	}
	

	private static boolean hasError(HttpResponse response) throws IOException {
		String statusCode = String.valueOf(response.getStatusLine().getStatusCode());
		return (isError(statusCode) || isServerError(statusCode));

	}

	private static boolean isServerError(String statusCode) {
		return checkCode(statusCode, 5);
	}
	private static boolean isError(String statusCode) {
		return checkCode(statusCode, 4);
	}
	private static boolean checkCode(String statusCode, int start){
		Pattern p = Pattern.compile("^"+ start + "[0-9]+");
		Matcher  m = p.matcher(statusCode);
		return m.find();
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public void setContas(List<Conta> contas) {
		this.contas = contas;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
	
}
