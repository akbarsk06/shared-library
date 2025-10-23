def call() {
    def jobName = env.JOB_NAME
    def buildNumber = env.BUILD_NUMBER
    def pipelineStatus = currentBuild.result ?: 'UNKNOWN'
    def bannerColor = pipelineStatus.toUpperCase() == 'SUCCESS' ? '#28a745' : '#dc3545'

    def body = """
    <!DOCTYPE html>
    <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Build Notification</title>
    </head>
    <body style="font-family: Arial, sans-serif; background-color: #f8f9fa; margin:0; padding:20px;">
      <table width="100%" cellpadding="0" cellspacing="0" border="0" style="max-width:600px; margin: auto; background-color: #ffffff; border: 1px solid #dee2e6; border-radius: 5px;">
        <tr>
          <td style="padding: 20px; border-bottom: 4px solid ${bannerColor};">
            <h2 style="margin: 0; color: #212529;">${jobName} - Build #${buildNumber}</h2>
          </td>
        </tr>
        <tr>
          <td style="padding: 15px; background-color: ${bannerColor}; border-radius: 0 0 5px 5px;">
            <h3 style="color: white; margin: 0; text-align: center; font-weight: normal;">
              Pipeline Status: ${pipelineStatus.toUpperCase()}
            </h3>
          </td>
        </tr>
        <tr>
          <td style="padding: 20px; color: #212529; font-size: 16px; line-height: 1.5;">
            <p>Dear Team,</p>
            <p>The build <strong>${jobName} #${buildNumber}</strong> has completed with status: <strong>${pipelineStatus.toUpperCase()}</strong>.</p>
            <p>You can check the detailed console output by clicking the link below:</p>
            <p style="text-align: center;">
              <a href="${env.BUILD_URL}" style="display: inline-block; padding: 10px 20px; background-color: ${bannerColor}; color: white; text-decoration: none; border-radius: 4px;">View Build Console Output</a>
            </p>
            <p>Best regards,<br/>CI/CD Jenkins Pipeline</p>
          </td>
        </tr>
        <tr>
          <td style="padding: 10px 20px; font-size: 12px; color: #6c757d; text-align: center; border-top: 1px solid #dee2e6;">
            <p style="margin: 0;">&copy; ${new Date().format('yyyy')} Your Company Name. All rights reserved.</p>
          </td>
        </tr>
      </table>
    </body>
    </html>
    """

    emailext (
      subject: "${jobName} - Build #${buildNumber} - ${pipelineStatus.toUpperCase()}",
      body: body,
      to: 'akbar.basha@1point1.in',
      from: 'jenkins.alert@1point1.in',
      replyTo: 'akbar.basha@1point1.in',
      mimeType: 'text/html'
    )
}
