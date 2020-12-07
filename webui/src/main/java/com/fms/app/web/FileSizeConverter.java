package com.fms.app.web;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fms.util.IOUtil;
import com.jedlab.framework.util.PersianDateConverter;

@Component
@Qualifier("fileSizeConverter")
public class FileSizeConverter implements Converter
{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value)
    {
        if (value == null || value.isEmpty())
        {
            return null;
        }
        try
        {
            return PersianDateConverter.getInstance().SolarToGregorian(value);
        }
        catch (Exception e)
        {
            throw new ConverterException();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value)
    {
        if (value == null)
        {
            return "";
        }
        else if (value instanceof Long)
        {
            Long fileSize = (Long) value;
            try
            {
                return IOUtil.humanReadableByteCountBin(fileSize);
            }
            catch (Exception e)
            {
                return "Conversion Error";
            }
        }
        else
        {
            throw new ConverterException();
        }
    }

}
