class ApplicationUser:
	def __init__(self, id=None, name=None, password=None, admin=False):
		self.id = id
		self.name = name
		self.password = password
		self.admin = admin
		self.summary = None
		self.current_puzzle = None
