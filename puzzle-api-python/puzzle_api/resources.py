import functools

import flask


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
			# TODO: parse auth_token as JWT.
			auth_request = auth_token
			if auth_request:
				# TODO: Save auth to some context? or at least just allow them in.
				pass
		else:
			return False
	except KeyError:
		return False

	return True
