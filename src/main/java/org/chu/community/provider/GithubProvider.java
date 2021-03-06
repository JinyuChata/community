package org.chu.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.chu.community.dto.AccessTokenDTO;
import org.chu.community.dto.GithubUser;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)     // post 使用.post
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            if (string.startsWith("error")) return null;
            String[] split = string.split("&");
            String access_token = split[0].split("=")[1];
            return access_token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String json = response.body().string();
            GithubUser githubUser = JSON.parseObject(json, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
