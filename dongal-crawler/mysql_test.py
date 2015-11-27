#-*- coding: utf-8 -*-
import sys

import MySQLdb

#===================================================================
# connect to mysql
#===================================================================

try:
    db = MySQLdb.connect(host="localhost", user="root", passwd="rkdrltkd",db="dongal")
except MySQLdb.Error, e:
    print "Error %d: %s" % (e.args[0], e.args[1])
    sys.exit (1)

cursor = db.cursor()

def getCategoryMetaDataAndLastSeq():
    cursor.execute("SELECT * FROM dongal.crawling_patterns")
    data = cursor.fetchall()

    for row in data :
        # idx, created_time, title_pattern, url_pattern, category_id, secret_pattern
        print "%d, %s, %s, %s, %d, %s" % (row[0], row[1], row[2], row[3], row[4], row[5])

getCategoryMetaDataAndLastSeq()
