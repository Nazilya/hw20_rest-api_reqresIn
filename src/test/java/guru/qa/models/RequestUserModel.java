package guru.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestUserModel {
    private String name;
    private String job;

}

/*  Request
{
    "name": "morpheus",
    "job": "leader"
}
 */