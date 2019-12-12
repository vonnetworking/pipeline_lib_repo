
import com.fhlmc.moderndelivery.mpl.*

print 'Checking JIRA Status....'

def code = new URL('https://pilot.agiletrailblazers.com/jira').openConnection().with {
    requestMethod = 'HEAD'
    connect()
    responseCode
}

if(responseCode == 200)
{
    println '200 OK'
}
else
{
    println responsecode + ' FAIL'
    throw new MPLModuleException("Jira health check failed: Response code " + responseCode)
}