from flask import Flask, request, render_template
import gunicorn
import platform

app = Flask(__name__)

@app.route("/")
def hello():
    return "Hello world!\n" + "Python version: " + platform.python_version() + "\n"
