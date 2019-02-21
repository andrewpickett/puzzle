from flask import Flask
from flask_restful import Api

app = Flask(__name__)
api = Api(app)

from puzzle_api import views, models, resources

api.add_resource(resources.UserLogin, '/login')
api.add_resource(resources.TokenRefresh, '/token/refresh')
api.add_resource(resources.AllUsers, '/users')
api.add_resource(resources.Main, '/main')
