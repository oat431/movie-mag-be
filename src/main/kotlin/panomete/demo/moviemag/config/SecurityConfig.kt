package panomete.demo.moviemag.config

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import panomete.demo.moviemag.security.service.AuthDetailServiceImpl
import panomete.demo.moviemag.security.utils.JWTEntryPoint
import panomete.demo.moviemag.security.utils.JWTFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
class SecurityConfig(
    private val jwtFilter: JWTFilter,
    private val jwtEntryPoint: JWTEntryPoint
) {

    var FREE_AREA: Array<String> = arrayOf(
        "/api-docs/**",
        "/swagger",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/webjars/**",
        "/favicon.ico",
        "/webjars/**",
        "/api/v1/test",
        "/api/v1/test/**"
    )

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return AuthDetailServiceImpl();
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Throws(Exception::class)
    protected fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider())
    }


    @Bean
    @Throws(java.lang.Exception::class)
    fun authenticationManager(
        authenticationConfiguration: AuthenticationConfiguration
    ): AuthenticationManager? {
        return authenticationConfiguration.authenticationManager
    }


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests {
            it.requestMatchers(*FREE_AREA).permitAll()
            it.anyRequest().authenticated()
        }
        http.exceptionHandling {
            it.authenticationEntryPoint(jwtEntryPoint)
        }
        http.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.cors {
            it.configurationSource(corsConfigurationSource())
        }
        http.csrf {
            it.disable()
        }
        http.authenticationProvider(authenticationProvider())
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    val allowedOrigin: List<String> = listOf(
        "http://localhost:3000",
        "http://localhost:4200",
        "http://localhost:4400"
    )

    @Bean
    fun corsConfigurationSource() : CorsConfigurationSource {
        val config : CorsConfiguration = CorsConfiguration();
        config.allowedOrigins = allowedOrigin;
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS");
        config.allowedHeaders = listOf("*");
        config.addExposedHeader("Access-Control-Allow-Origin");
        val source : UrlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
