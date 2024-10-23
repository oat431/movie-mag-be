package panomete.demo.moviemag.test.service

import org.springframework.stereotype.Service

@Service
class TestServiceImpl : TestService{
    override fun test(): String {
        return "Hello World"
    }
}