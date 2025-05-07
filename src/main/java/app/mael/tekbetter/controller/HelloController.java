package app.mael.tekbetter.controller;

import app.mael.tekbetter.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes a simple greeting endpoint.
 * <p>
 * This controller provides an API endpoint to return a welcome message.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Hello", description = "Endpoints for greeting and testing API connectivity")
public class HelloController {

    private final HelloService helloService;

    /**
     * Constructs a new HelloController with the specified HelloService.
     *
     * @param helloService the service responsible for providing the greeting message
     */
    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * GET endpoint to retrieve a greeting message.
     *
     * @return a {@link ResponseEntity} containing the greeting message and HTTP status 202 (Accepted)
     */
    @Operation(summary = "Get a greeting message", description = "Returns a static welcome message from the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Greeting message successfully returned")
    })
    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>(helloService.getHelloMessage(), HttpStatus.ACCEPTED);
    }
}