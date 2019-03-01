import jsonpickle
from flask import jsonify, redirect, request, make_response
from flask_cors import CORS

import puzzle_api.services
from main import app
from puzzle_api.jwt_utils import protect_with_jwt, get_jwt_object_from_request

CORS(app, supports_credentials=True, expose_headers=["Authorization", "WWW-Authenticate", "X-User-Admin"])


@app.route('/login', methods=['POST'])
def login():
    resp = make_response("")
    try:
        jwt_token = puzzle_api.services.perform_login(request.json["name"], request.json["password"].encode("utf-8"))
        resp.headers["Authorization"] = str(jwt_token)
    except PermissionError:
        resp = make_response("Invalid credentials", 401)
    except TypeError:
        resp = make_response("Something went horribly wrong", 500)
    return resp


@app.route('/', methods=['GET'])
@protect_with_jwt
def get_current_puzzle_for_user():
    auth = get_jwt_object_from_request(request)
    if auth:
        user = puzzle_api.services.load_puzzle_for_user(auth["userId"])
        return jsonpickle.encode(user, unpicklable=False)
    else:
        return "You are not allowed to view this page", 401


@app.route('/hint', methods=['GET'])
@protect_with_jwt
def get_hint():
    return jsonify({'message': 'Get a new hint'})


@app.errorhandler(401)
def custom_401(error):
    return redirect("/login")
