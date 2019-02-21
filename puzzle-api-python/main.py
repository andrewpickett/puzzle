from flask import Flask, jsonify, Response, redirect

from puzzle_api.resources import protect_with_jwt

app = Flask(__name__)


@app.route('/', methods=['GET'])
@protect_with_jwt
def get_current_puzzle_for_user():
	return jsonify({'message': 'Hello, World!'})


@app.errorhandler(401)
def custom_401(error):
	# redirect("/login")
	return Response("You do not have permission to this page", 401, {'WWW-Authenticate': 'Bearer '})
