/**
 *  @author jefersonlpsilva
 *  @since May 4, 2021
 */
package app.dinamismo.restBase.services.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClienteInsert {
	
	String message() default"Errode validação";
	
	Class<?>[]groups()default{};
	
	Class<?extends Payload>[]payload()default{};
}