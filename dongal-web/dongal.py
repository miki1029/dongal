import smtplib

from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText

# me == my email address
# you == recipient's email address
me = "no-reply@gmail.com"
you = "kang8530@gmail.com"

# Create message container - the correct MIME type is multipart/alternative.
msg = MIMEMultipart('alternative')
msg['Subject'] = "Link"
msg['From'] = me
msg['To'] = you

def init_mail(userIdx):
    # Create the body of the message (a plain-text and an HTML version).
    text = "[Dongal] Vefiry email"
    html = """\
    <html>
      <head></head>
      <body>
        <p>[Dongal] Verify email<br></p>
        <form action="http://192.168.0.151:8080/session/verifyDGU">
            <input type="hidden" name="userIdx" value="%s" />
            <input type="submit" value="verify" />
        </form>
      </body>
    </html>
    """ % userIdx

    # Record the MIME types of both parts - text/plain and text/html.
    part1 = MIMEText(text, 'plain')
    part2 = MIMEText(html, 'html')

    # Attach parts into message container.
    # According to RFC 2046, the last part of a multipart message, in this case
    # the HTML message, is best and preferred.
    msg.attach(part1)
    msg.attach(part2)

def send_mail():
    # Send the message via local SMTP server.
    mail = smtplib.SMTP('smtp.gmail.com', 587)

    mail.ehlo()

    mail.starttls()

    mail.login('dgu.dna@gmail.com', 'dnalinux12345')
    mail.sendmail(me, you, msg.as_string())
    mail.quit()



