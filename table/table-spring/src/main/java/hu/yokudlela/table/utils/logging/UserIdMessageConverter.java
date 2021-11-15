/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.yokudlela.table.utils.logging;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import hu.yokudlela.table.spring.ApplicationContextProvider;
import hu.yokudlela.table.utils.request.RequestBean;
import hu.yokudlela.table.utils.request.RequestFilter;
import net.logstash.logback.marker.ObjectAppendingMarker;

/**
 *
 * @author oe
 */

public class UserIdMessageConverter extends ClassicConverter{

    ApplicationContextProvider appContext = new ApplicationContextProvider();

    
    @Override
    @AspectLogger
    public String convert(ILoggingEvent event) {

        try {
            RequestBean request = appContext.getApplicationContext().getBean("requestScopedBean", RequestBean.class);
            if (request != null) {
                return ("" + request.getUser());
            }
        } catch (Exception e) {
            if (event != null && event.getArgumentArray() != null) {
                ObjectAppendingMarker tmp;
                for (Object bean : event.getArgumentArray()) {
                    if (bean instanceof ObjectAppendingMarker) {
                        tmp = (ObjectAppendingMarker) bean;
                        if (RequestFilter.USER_ID.equals(tmp.getFieldName())) {
                            return "" + tmp.getFieldValue();
                        }
                    }
                }
            }
        }
        return RequestFilter.NOT_DEF;
    }
    
}
