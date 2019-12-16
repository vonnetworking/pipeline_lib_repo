
import com.fhlmc.moderndelivery.mpl.*


import java.net.HttpURLConnection;
import java.net.URL;

print 'Checking Fluentd Status....'

HttpURLConnection urlConnection;
def responseCode
def targetUrl = 'https://pilot.agiletrailblazers.com/fluentd'

try {
    urlConnection = (HttpURLConnection) new URL(targetUrl).openConnection()
    urlConnection.setRequestMethod("HEAD")
    urlConnection.setConnectTimeout(2000)
    urlConnection.setReadTimeout(2000)
    responseCode = urlConnection.getResponseCode();

} catch (Exception e) {
    e.printStackTrace()
    responseCode = 0
}


if(responseCode == 200)
{
    print 'OK'
    println ''
}
else
{
    println responseCode + ' FAIL'
    throw new MPLModuleException("Fluentd health check failed: Response code " + responseCode)
}