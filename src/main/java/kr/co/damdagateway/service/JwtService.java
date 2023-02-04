package kr.co.damdagateway.service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import kr.co.enums.ErrorCode;
import kr.co.error.exception.JwtTokenExpiredException;
import kr.co.error.exception.JwtTokenInvalidException;
import kr.co.error.exception.UserDefineException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * 시크릿 키를 Base64로 인코딩을 하는 메소드.
     */
    @PostConstruct
    protected void init(){
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    /**
     * 토큰의 유효성을 판단하는 메소드
     * @param token : 토큰
     * @return 토큰이 만료되었는지에 대한 불리언 값
     * @exception ExpiredJwtException 토큰이 만료되었을 경우에 발생하는 예외
     */
    public boolean isJwtValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(generateKey())
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT Signature");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token");
            throw new JwtTokenExpiredException(ErrorCode.FAIL_EXPIRE);
        } catch (IllegalArgumentException e) {
            log.error("Empty JWT Claims");
            throw new JwtTokenInvalidException(ErrorCode.FAIL_INVALID_CLAIMS);
        }
    }

    /**
     * 키 변환을 위한 key 를 만드는 메서드
     * @return secret key
     */
    private byte[] generateKey(){
        try{
            return SECRET_KEY.getBytes("UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new UserDefineException("키 변환에 실패하였습니다. ", e.getMessage());
        }
    }

    public void isRequestHeaders(ServerHttpRequest request){
        if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
            throw new JwtTokenInvalidException(ErrorCode.FAIL_NO_AUTHORIZATION_HEADER);
        }
    }

    public String resolveToken(ServerHttpRequest request){
        return request.getHeaders()
                .get(HttpHeaders.AUTHORIZATION).get(0)
                .replace("Bearer", "").trim();
    }
}
