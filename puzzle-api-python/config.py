config = {
    'jwt': {
        'secret_key': 'MySuperSecretKey',
        'expire_seconds': 3600,
        'algorithm': 'HS512',
        'issuer': 'ProfoundDistortion'
    },
    'database': {
        'username': 'root',
        'password': 'abcd.1234',
        'host': 'localhost',
        'port': 3306,
        'schema': 'puzzle',
        'pool_name': 'puzzle_pool',
        'pool_size': 3,
        'autocommit': True
    }
}
