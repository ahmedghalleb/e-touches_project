package com.echances.etouches.utilities;

/**
 * 
 * @file Constants.java
 * @author Ahmed Ghalleb
 * @version 1.0
 * @brief * class used for application constants.
 * @details *
 * 
 */
public final class Constants
{

	/**
	 * Method Constructor
	 */
	private Constants()
	{
	}

	/**
	 * 
	 * class used for extra intent constants
	 * 
	 */
	public static class Extra_Intent
	{

		public static final int		PICK_FROM_CAMERA			= 0;
		public static final int		PICK_FROM_GALLERY			= 1;
		public static final int		CROP_IMAGE					= 2;

		public static final String	LOGIN_PUSH_NOTIFICATION		= "login_user";
		public static final String	INVIT_PUSH_NOTIFICATION		= "invit_user";
		public static final String	MESSAGE_PUSH_NOTIFICATION	= "message_user";
		public static final String	WithPush					= "WithPush";

	}

	/**
	 * 
	 * class used for Params constants
	 * 
	 */
	public static class Params
	{
		public static final String	TAG			= "ETOUCHES";
		public static final String	TAG_WS		= "ETOUCHES_WS";
		public static final String	PREFS_NAME	= "ETOUCHES_CACHE";

	}

	/**
	 * 
	 * class used for Preferences Cache constants
	 * 
	 */
	public static class PreferencesCache
	{
		public static final String	USER_LOGIN		= "user_login";
		public static final String	USER_TOKEN		= "user_token";
		public static final String	IS_FIRST_RUN	= "is_first_run";
		public static final String	USER_ID		= "user_id";

	}

	

	/**
	 * 
	 * class used for Facebook Utilities constants
	 * 
	 */
	public static class Instagram
	{
		public static final String CLIENT_ID = /*"7bf7adb692874edf97259228d083ce1a";//*/"379d744556c743c090c8a2014779f59f";
		public static final String CLIENT_SECRET = /*"e3e675155b8e40e9a7dbc46be9a28a3a";//*/"fd6ec75e44054da1a5088ad2d72f2253";
		public static final String CALLBACK_URL = "instagram://connect";
	}

	/**
	 * 
	 * class used for ParamsWebService constants
	 * 
	 */
	public static class ParamsWebService
	{

		// public static final String SERVER_NAME = "http://172.16.1.67/app_dev.php";
		public static final String	SERVER_NAME				= "http://api.itouches.net/";

		

		public static final String	SUBSCRIPTION			= "/reg_user.ashx";
		public static final String	LOGIN					= "/auth_user.ashx";
		public static final String	GETSERVICES				= "/get_services.ashx";
		public static final String	GET_PROVIDER_SERVICES	= "/ServiceProviders/get_provider_service.ashx";
		public static final String	GETONESERVICES			= "/ServiceProviders/get_one_service.ashx";
		public static final String	ADD_SERVICE	            = "/ServiceProviders/add_service.ashx";
		public static final String	UPDATE_SERVICE	        = "/ServiceProviders/edit_service.ashx";
		public static final String	GET_SCHEDULE			= "/ServiceProviders/get_service_provider_schedule.ashx";
		public static final String	SET_SCHEDULE			= "/ServiceProviders/set_service_provider_schedule.ashx";
		public static final String	UPLOAD_IMAGE			= "/ServiceProviders/upload.ashx";
		public static final String	UPLOAD_IMAGE_URL		= "/ServiceProviders/upload_url.ashx";
		public static final String	DELETE_IMAGE			= "/ServiceProviders/delete_provider_service_gallery.ashx";
		public static final String	FORGET_PASSWORD			= "/Profile/forget_account.ashx";
		public static final String	GET_PROFILE				= "/Profile/get_info.ashx";
		public static final String	EDIT_PROFILE			= "/Profile/edit_info.ashx";
		public static final String	UPDATE_LOCATION			= "/Profile/edit_location.ashx";

		public final int			timeout					= 30000;

		public static String		LANG					= java.util.Locale.getDefault().getLanguage();

	}

	/**
	 * 
	 * class used for HTTP Params constants
	 * 
	 */
	public static class HTTPConstantParams
	{
		
		public final static String	USERNAME					= "un";
		public final static String	PASSWORD					= "ps";
		public final static String	TYPE						= "t";
		public final static String	MOBILE						= "mb";
		
		
		public final static String	VALUE						= "value";
		public final static String	TOKEN						= "token";
		public final static String	LANG						= "lang";
		public final static String	CONNEXION_ERROR_WS			= "CONNEXION_ERROR_WS";

		public final static String	STATUS						= "status";
		public final static String	AUTHOR_LOGIN				= "authorLogin";
		public final static String	Q							= "q";
		public final static String	USER_LOGIN					= "userLogin";
		public final static String	TYPEs						= "type";
		public final static String	LIKE						= "like";
		public final static String	RESPONSE_ID					= "responseId";
		public final static String	LOGIN_DELETE				= "login";

		public final static String	RESULT_WS_SUCCES			= "ok";
		public final static String	RESULT_WS_FAILED			= "error";

		public final static String	LOGIN						= "_username";
		
		public final static String	FIRST_NAME					= "first_name";
		public final static String	LAST_NAME					= "last_name";
		public final static String	EMAIL						= "email";
		public final static String	PHOTO_URL					= "photo_url";
		public final static String	CODE_POSTAL					= "postal_code";
		public final static String	CITY						= "city";
		public final static String	PAYS						= "country";
		public final static String	BIRTHDAY					= "birthday";
		public final static String	FAMILIYSITUATION			= "familySituation";
		public final static String	SEXE						= "sexe";
		public final static String	CIVILITY					= "civility";


	}

}
