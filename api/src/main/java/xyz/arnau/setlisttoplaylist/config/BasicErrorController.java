package xyz.arnau.setlisttoplaylist.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;
import static org.springframework.boot.web.error.ErrorAttributeOptions.of;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class BasicErrorController extends AbstractErrorController {
	public BasicErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}

	@RequestMapping
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, of(MESSAGE));
		HttpStatus status = getStatus(request);
		return new ResponseEntity<>(body, status);
	}
}