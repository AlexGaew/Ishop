package net.GtwoA.ishop.service;

import net.GtwoA.ishop.model.SocialAccount;

public interface SocialService {
	String getAuthorizeUrl();

	SocialAccount getSocialAccount(String authToken);

}
