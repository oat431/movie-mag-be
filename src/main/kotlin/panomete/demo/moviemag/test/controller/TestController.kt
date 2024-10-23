package panomete.demo.moviemag.test.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import panomete.demo.moviemag.common.entity.Status
import panomete.demo.moviemag.common.payload.response.ResponseDto
import panomete.demo.moviemag.test.service.TestService

@Controller
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "Test Controller", description = "Test Controller")
class TestController(
    private val testService: TestService
) {

    @GetMapping
    @Operation(summary = "Test endpoint")
    fun test(): ResponseEntity<ResponseDto<String>> {
        val message: String = testService.test()
        val response :ResponseDto<String> = ResponseDto(message, null, Status.SUCCESS)
        return ResponseEntity(response, HttpStatus.OK)
    }
}