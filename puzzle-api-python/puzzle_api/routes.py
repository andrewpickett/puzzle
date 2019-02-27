from flask import jsonify, redirect, request

from puzzle_api.jwt_utils import protect_with_jwt, generate_jwt_token

from main import app


@app.route('/', methods=['GET'])
@protect_with_jwt
def get_current_puzzle_for_user():
	return jsonify({'message': 'Hello, World!'})


@app.route('/hint', methods=['GET'])
@protect_with_jwt
def get_hint():
	return jsonify({'message': 'Get a new hint'})


@app.route('/login', methods=['GET', 'POST', 'OPTIONS'])
def login():
	print("")
	if request.method == 'GET':
		return jsonify({'message': 'Login page here!'})
	else:
		# get form data, look up user, validate credentials.
		jwt_token = generate_jwt_token(1, 'ROLE_ADMIN', 'Andrew')
		return jsonify({'args': request.args, 'form': request.form, 'jwt': str(jwt_token)})


@app.errorhandler(401)
def custom_401(error):
	return redirect("/login")
