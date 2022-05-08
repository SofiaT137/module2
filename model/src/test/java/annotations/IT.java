package annotations;

import com.epam.esm.configuration.DevelopmentConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfiguration.class)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("dev")
@Sql({
        "classpath:data.sql"
})
public @interface IT {
}
