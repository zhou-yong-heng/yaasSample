
package com.sample.wishlistDemo.api.generated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.wishlistDemo.api.generated.WishlistItem;
import com.sample.wishlistDemo.api.generated.YaasAwareParameters;
import com.sample.wishlistDemo.bean.AccessTokenResponse;
import com.sample.wishlistDemo.util.HttpRequestUtil;
import com.sample.wishlistDemo.util.ObjectMapperUtil;

/**
* Resource class containing the custom logic. Please put your logic here!
*/
@Component("apiWishlistsResource")
@Singleton
public class DefaultWishlistsResource implements com.sample.wishlistDemo.api.generated.WishlistsResource
{
	@javax.ws.rs.core.Context
	private javax.ws.rs.core.UriInfo uriInfo;
	
	private String yaasViewScope = "hybris.document_view";
	
	private String yaasManageScope = "hybris.document_manage";

	/* GET / */
	@Override
	public Response get(final YaasAwareParameters yaasAware)
	{
		// place some logic here
		return Response.ok()
			.entity(new java.util.ArrayList<Wishlist>()).build();
	}

	/* POST / */
	@Override
	public Response post(final YaasAwareParameters yaasAware, final Wishlist wishlist)
	{
		/** get access token with a manage scope **/
		String accessToken = getAccessToken(yaasManageScope);
		String wishListObj = ObjectMapperUtil.toJson(wishlist);

		/** define yaas document service and put access token **/
		String addwishListUrl = "https://api.beta.yaas.io/hybris/document/v1/caashiring/caashiring.test/data/e1234";
		String authorizationForAddWishList = "Bearer " + accessToken;
		String contentTypeForAdd = "application/json";
		Map<String, String> headersForMap = new HashMap<String, String>();
		headersForMap.put("Authorization", authorizationForAddWishList);
		headersForMap.put("Content-Type", contentTypeForAdd);

		/** post data to document service **/
		HttpRequestUtil.sendPost(addwishListUrl, null, wishListObj, headersForMap);

		// place some logic here
		return Response.created(uriInfo.getAbsolutePath()).build();
	}

	/* GET /{wishlistId} */
	@Override
	public Response getByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.ok()
			.entity(new Wishlist()).build();
	}

	/* PUT /{wishlistId} */
	@Override
	public Response putByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId, final Wishlist wishlist)
	{
		// place some logic here
		return Response.ok()
			.build();
	}

	/* DELETE /{wishlistId} */
	@Override
	public Response deleteByWishlistId(final YaasAwareParameters yaasAware, final java.lang.String wishlistId)
	{
		// place some logic here
		return Response.noContent()
			.build();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public
	Response getByWishlistIdWishlistItems(
			final YaasAwareParameters yaasAware,  final java.lang.String wishlistId)
	{
		/** get access token with view scope. **/
		String accessToken = getAccessToken(yaasViewScope);

		String getTotalWishListUrl = "https://api.beta.yaas.io/hybris/document/v1/caashiring/caashiring.test/data/e1234";
		String authorizationForAddWishList = "Bearer " + accessToken;
		String contentTypeForGet = "application/json";
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("Authorization", authorizationForAddWishList);
		headersMap.put("Content-Type", contentTypeForGet);

		/** get with list. **/
		String wishLists = HttpRequestUtil.sendGet(getTotalWishListUrl, null, headersMap);
		Object wishListObj = ObjectMapperUtil.fromJson(wishLists, ArrayList.class);
		List<Map> wishListCollection = (ArrayList<Map>) wishListObj;

		java.util.ArrayList<WishlistItem> items = new java.util.ArrayList<WishlistItem>();

		for (Map map : wishListCollection) {
			map.remove("metadata");
			Wishlist wishList = (Wishlist) ObjectMapperUtil.fromJson(ObjectMapperUtil.toJson(map), Wishlist.class);
			if (wishList.getItems() != null) {
				items.addAll(wishList.getItems());
			}
		}
		return Response.ok().entity(items).build();
	}

	@Override
	public
	Response postByWishlistIdWishlistItems(final YaasAwareParameters yaasAware,
			final java.lang.String wishlistId, final WishlistItem wishlistItem){
		// place some logic here
		return Response.noContent()
					.build();
	}
	
	/** get access token from Yaas **/
	public String getAccessToken(String scope){
		String url = "https://api.beta.yaas.io/hybris/oauth2/v1/token";
	    String params = "client_id=R64UWNPnzFwkg74XAYiVc6mWpB0Qtu1R&client_secret=EEmhYGZXGXPkjBpH&scope="+scope+"&grant_type=client_credentials";
	    String accessTokenResponse = HttpRequestUtil.sendPost(url, params, null, null);
	    ObjectMapper mapper = new ObjectMapper();
	    AccessTokenResponse response = null;
	    try {
	    	response = mapper.readValue(accessTokenResponse, AccessTokenResponse.class);
		} catch (JsonParseException e) {
			throw new RuntimeException(e);
		} catch (JsonMappingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	    return response.getAccess_token();
	}
}
