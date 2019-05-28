import sqlite3


def trust_user_pass(_user, _pass):
    try:
        conn = sqlite3.connect('mydb.db')
        c = conn.cursor()
        res = c.execute("SELECT username, password FROM user")
    except Exception as e:
        print(e)
        conn.close()
        return False
    for record in res:
        user = record[0]
        password = record[1]
        if _user == user and _pass == password:
            return True
    return False
    conn.close()


def get_filter_word():
    try:
        conn = sqlite3.connect('mydb.db')
        c = conn.cursor()
        res = c.execute("SELECT word FROM badword")
        return res
    except Exception as e:
        print(e)
        conn.close()
        return False
    conn.close()
