from main import app
from flask import jsonify


@app.route('/')
def get_current_puzzle_for_user():
	# Call "userService.getUserFromJWTToken(true)"
	# Should just be a GET
	return jsonify({'message': 'Hello, World!'})
