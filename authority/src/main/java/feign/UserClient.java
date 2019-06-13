package feign;

import com.example.foundation.dto.Response;
import dto.request.LoginRequest;
import dto.response.LoginResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoufan
 * @date 2019/6/13 09:56
 * @description
 */
@FeignClient("user-service")
public interface UserClient {

    @RequestMapping("/users/login")
    Response<LoginResponse> login(@RequestBody LoginRequest request);
}
