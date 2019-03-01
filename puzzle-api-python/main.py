from flask import Flask

app = Flask(__name__)

import puzzle_api.routes  # noqa: E402
