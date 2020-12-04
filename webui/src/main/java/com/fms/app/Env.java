package com.fms.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Omid Pourhadi
 *         <p>
 *         <b>Can not be used in JSF Managed Bean</b>
 *         </p>
 */
@Slf4j
public final class Env
{

    public static final String NL = System.getProperty("line.separator");
    public static final String FILE_SEPARATOR = File.separator;
    public static final String JBOSS7_DATA_HOME = System.getProperty("jboss.server.data.dir");
    public static final String JBOSS7_LOG_HOME = System.getProperty("jboss.server.log.dir");
    public static final String OS = System.getProperty("os.name");
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String USER_HOME = System.getProperty("user.home");

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat sdf_without_tz = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat sdf_with_time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static final DecimalFormat df = new DecimalFormat("###,###");

    public static final RangeDayDate RangeDay = new RangeDayDate();

    public static InputStream getFileAsStream(String fileName)
    {
        InputStream is = null;
        is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        if (is == null)
            is = Env.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null)
            is = Env.class.getResourceAsStream(fileName);
        if(is == null)
        {
            Resource resource = new ClassPathResource("classpath:"+fileName);
            try
            {
                is = resource.getInputStream();
            }
            catch (IOException e)
            {
                return null;
            }
        }
        return is;

    }

    public static class RangeDayDate
    {

        public Date getFromDate()
        {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            return c.getTime();
        }

        public Date getToDate()
        {
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            c2.set(Calendar.HOUR_OF_DAY, 23);
            c2.set(Calendar.MINUTE, 59);
            c2.set(Calendar.SECOND, 59);
            c2.set(Calendar.MILLISECOND, 59);
            return c2.getTime();
        }

    }

    private static Properties prop = new Properties();

    private Env()
    {

    }

    static
    {
        reload();
    }
    
    

    public static synchronized void reload()
    {
        InputStream stream = null;
        try
        {
            stream = getFileAsStream("/conf.properties");
            prop.load(stream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            IOUtils.closeQuietly(stream);
        }
    }

    
    public static boolean isDev()
    {
        return Boolean.parseBoolean(prop.getProperty("dev"));
    }
    
    public static BigInteger getDiskSizeLimit()
    {
        return new BigInteger(prop.getProperty("disk_size_limit"));
    }
    
    
   

}
