package panomete.demo.moviemag.security.service

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import kotlin.jvm.Throws

class AuthDetailServiceImpl: UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails {
        throw UsernameNotFoundException("User not found")
    }
}