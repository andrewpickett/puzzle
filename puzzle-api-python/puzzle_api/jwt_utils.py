import datetime
import functools

import flask
import jwt

from config import JWT_SECRET_KEY, JWT_EXPIRE_MILLIS


def generate_jwt_token(user_id, role, name):
	"""
	Generates an encoded JWT token to be used for auth on all future requests for this user.

	:param user_id: The ID of the user logged in.
	:param role: The role(s) that the user has.
	:param name: The name of the user logged in.
	:return: an encoded JWT token to be used for auth on future requests.
	"""
	return jwt.encode({
		'userId': user_id,
		'role': role,
		'sub': name,
		'iat': datetime.datetime.utcnow(),
		'iss': 'ProfoundDistortion',
		'exp': datetime.datetime.utcnow() + datetime.timedelta(milliseconds=JWT_EXPIRE_MILLIS)
	}, JWT_SECRET_KEY, algorithm='HS512').decode('utf-8')


def protect_with_jwt(f):
	"""
	This is a decorator function for validating requests from the user to ensure they have a valid JWT token
	on them. If the JWT doesn't exist or is invalid, then flask will abort the request with a 401 status code.

	:param f: The function to decorate
	:return: The decorator for the function
	"""
	@functools.wraps(f)
	def wrapper(*args, **kwargs):
		if not check_jwt_filter(flask.request):
			flask.abort(401)

		return f(*args, **kwargs)
	return wrapper


def check_jwt_filter(request):
	"""
	Validates that the request contains a valid JWT token.

	:param request: The request from the user to validate.
	:return: True if the request has a valid JWT token, False otherwise.
	"""
	try:
		header = request.headers["Authorization"]
		if header.startswith('Bearer '):
			auth_token = header.replace('Bearer ', '')
			auth_request = parse_jwt_token(auth_token)

			if auth_request and auth_request["iss"] == 'ProfoundDistortion' and datetime.datetime.utcnow() < datetime.datetime.utcfromtimestamp(auth_request["exp"]):
				# TODO: Save auth to some context? or at least just allow them in.
				pass
			else:
				raise KeyError("Couldn't parse claims and/or expired token detected.")
		else:
			return False
	except KeyError:
		return False

	return True


def parse_jwt_token(jwt_token):
	"""
	Decode the encoded JWT token into the object.

	:param jwt_token: The JWT token to decode
	:return: The decoded JWT object
	"""
	try:
		return jwt.decode(jwt_token, JWT_SECRET_KEY, algorithms=['HS512'])
	except jwt.InvalidTokenError:
		return None


# if __name__ == '__main__':
# 	token = generate_jwt_token(1, 'ROLE_ADMIN', 'Andrew')
#
# 	print(token)
# 	print(parse_jwt_token(token))
