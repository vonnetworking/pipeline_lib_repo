
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
    throw new MPLException("Jira health check failed: Response code " + responseCode)
}