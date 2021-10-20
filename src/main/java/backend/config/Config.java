package backend.config;

import lombok.Data;

@Data
public class Config {

    String code = "status", message = "message";
    public String code_sukses = "200";
    public String code_server = "500";
    public String code_notFound = "404";
    public String codeRequired = "403";
    public String code_null = "1";
    public String message_sukses = "sukses";
    public String message_alreadyexist = " already exist";
    public String notFoundUserID = "User id not found.";
    public String notFound = " not found.";
    public String isRequired = " is required";
    public String statusActive = "active";
    public String statusInActive = "inactive";
    public String statusPublish = "publish";
    public String statusNotPublish = "notpublished";
    public String statusDraft = "draft";

}
