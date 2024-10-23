package panomete.demo.moviemag.security.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.Serializable
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.HashMap

@Component
class JWTUtil(
    @Value("\${jwt.secret}") private val SECRET: String,
    @Value("\${jwt.user.expiration}") private val USER_EXPIRATION: Long,
    @Value("\${jwt.admin.expiration}") private val ADMIN_EXPIRATION: Long,
    @Value("\${jwt.refresh.expiration}") private val REFRESH_EXPIRATION: Long
) : Serializable {

    companion object {
        private const val CLAIM_KEY_USERNAME = "sub"
        private const val CLAIM_KEY_ID = "id"
        private const val CLAIM_KEY_ROLE = "role"
        private const val CLAIM_KEY_CREATED = "created"
    }




//    fun generateAccessToken(user: Auth): String {
//        val roles = user.simpleAuthorities
//        val role = roles.firstOrNull() ?: ""
//        val isAdmin = role == "ROLE_ADMIN" || role == "ROLE_SUPER_ADMIN"
//
//        val claims = mutableMapOf<String, Any>()
//        claims[CLAIM_KEY_ID] = user.id.toString()
//        claims[CLAIM_KEY_USERNAME] = user.username
//        claims[CLAIM_KEY_ROLE] = simplifyRole(roles)
//        claims[CLAIM_KEY_CREATED] = Date()
//
//        return generateToken(claims, if (isAdmin) ADMIN_EXPIRATION else USER_EXPIRATION)
//    }

//    fun generateRefreshToken(token: String, user: Auth): String {
//        val claims = getClaimsFromToken(token)
//        val roles = user.simpleAuthorities
//
//        claims[CLAIM_KEY_ID] = user.id.toString()
//        claims[CLAIM_KEY_USERNAME] = user.username
//        claims[CLAIM_KEY_ROLE] = simplifyRole(roles)
//        claims[CLAIM_KEY_CREATED] = Date()
//
//        return generateToken(claims, REFRESH_EXPIRATION)
//    }

//    fun mockGenerateAccessToken(user: Auth, expiration: Long): String {
//        val roles = user.simpleAuthorities
//        val claims = mutableMapOf<String, Any>()
//
//        claims[CLAIM_KEY_ID] = user.id.toString()
//        claims[CLAIM_KEY_USERNAME] = user.username
//        claims[CLAIM_KEY_ROLE] = simplifyRole(roles)
//        claims[CLAIM_KEY_CREATED] = Date()
//
//        return generateToken(claims, expiration)
//    }

    private fun simplifyRole(role: List<String>): String {
        return role.firstOrNull()?.substring(5) ?: ""
    }

//    fun generateToken(claims: Map<String, Any>, expiration: Long): String {
//        val key: Key = SecretKeySpec(SECRET.toByteArray(), SignatureAlgorithm.HS512.jcaName)
//
//        return Jwts.builder()
//            .setClaims(claims)
//            .signWith(key, SignatureAlgorithm.HS512)
//            .setExpiration(generateExpirationDate(expiration))
//            .compact()
//    }

//    fun getClaimsFromToken(token: String): Claims {
//        val key: Key = SecretKeySpec(SECRET.toByteArray(), SignatureAlgorithm.HS512.jcaName)
//
//        return Jwts.parserBuilder()
//            .setSigningKey(key)
//            .build()
//            .parseClaimsJws(token)
//            .body
//    }

    fun generateExpirationDate(expiration: Long): Date {
        return Date(System.currentTimeMillis() + expiration * 1000)
    }

//    fun getUsernameFromToken(token: String): String? {
//        return getClaimsFromToken(token).subject
//    }

//    fun getExpirationDateFromToken(token: String): Date? {
//        return getClaimsFromToken(token).expiration
//    }

//    fun getCreatedDateFromToken(token: String): Date? {
//        return getClaimsFromToken(token)[CLAIM_KEY_CREATED] as? Date
//    }

//    fun isTokenExpired(token: String): Boolean {
//        val expiration = getExpirationDateFromToken(token)
//        return expiration?.before(Date()) ?: true
//    }

//    fun isTokenValid(token: String, user: Auth): Boolean {
//        val username = getUsernameFromToken(token)
//        return (username == user.username && !isTokenExpired(token))
//    }

    fun isCreatedBeforeLastPasswordReset(created: Date?, lastPasswordReset: Date?): Boolean {
        return created?.before(lastPasswordReset) ?: false
    }

//    fun canTokenBeRefreshed(token: String, lastPasswordReset: Date?): Boolean {
//        val created = getCreatedDateFromToken(token)
//        return (!isCreatedBeforeLastPasswordReset(created, lastPasswordReset) && !isTokenExpired(token))
//    }
}
