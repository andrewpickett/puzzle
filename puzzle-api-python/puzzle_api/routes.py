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


@app.route('/hint', methods=['POST'])
@protect_with_jwt
def get_hint():
    hint = puzzle_api.services.get_hint(int(request.args['puzzleId']))
    return jsonify(hint is None)


@app.route('/answer', methods=['POST'])
@protect_with_jwt
def submit_answer():
    earned_score = int(request.args['earnedScore'])
    answer = jsonpickle.decode(request.data.decode('utf-8'))

    return jsonify(puzzle_api.services.submit_answer(answer, earned_score))


@app.errorhandler(401)
def custom_401(error):
    return redirect("/login")
