package guru.qa.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserByBuilder {
    private String name;
    private String job;
}

/*  Request
{
    "name": "morpheus",
    "job": "leader"
}
 */