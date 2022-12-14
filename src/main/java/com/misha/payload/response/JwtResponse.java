package com.misha.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Integer id;
  private String email;
  private String contactname;
  private List<String> roles;

  public JwtResponse(String accessToken, Integer id, String email, String contactname, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.email = email;
    this.contactname = contactname;
    this.roles = roles;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Integer getId() {
    return id;
  }
  

  public String getContactname() {
	return contactname;
}

public void setContactname(String contactname) {
	this.contactname = contactname;
}

public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getRoles() {
    return roles;
  }
}
