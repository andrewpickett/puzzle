from flask_restful import Resource, reqparse
import json

from .services import UserService

parser = reqparse.RequestParser()

user_service = UserService()

class UserLogin(Resource):
	def post(self):
		#parser.add_argument('username', help='This field cannot be blank', required=True)

		return {'message': 'User login'}


class UserLogout(Resource):
	def post(self):
		return {'message': 'User logout'}


class TokenRefresh(Resource):
	def post(self):
		return {'message': 'Token refresh'}


class AllUsers(Resource):
	def get(self):
		data = parser.parse_args()
		return data


class Main(Resource):
	def get(self):
		return json.dumps(user_service.get_user().__dict__)
