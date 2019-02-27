import configparser

config = configparser.ConfigParser()
config.read('config.ini')

JWT_SECRET_KEY = config['JWT']['SECRET_KEY']
JWT_EXPIRE_MILLIS = config['JWT']['EXPIRE_MILLIS']
