package org.otomotive.pcb.interceptor;

import lombok.*;

import java.lang.reflect.Parameter;

/**
 * Method parameter.
 *
 * @author David CABILLIC
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodParameter {

    private String name;
    private Parameter parameter;
    private Object value;
}
