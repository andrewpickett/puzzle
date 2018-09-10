<template>
	<div class="container">
		<form>
			<div class="form-group row">
				<label for="user" class="col-sm-2 col-form-label">User</label>
				<div class="col-sm-10">
					<select id="user" class="form-control">
						<option selected>Choose...</option>
						<option v-for="user in users" :value="user.id">
							{{ user.name }}
						</option>
					</select>
				</div>
			</div>
			<!--<div class="form-group row">
				<label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="inputPassword3" placeholder="Password">
				</div>
			</div>
			<fieldset class="form-group">
				<div class="row">
					<legend class="col-form-legend col-sm-2">Radios</legend>
					<div class="col-sm-10">
						<div class="form-check">
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios1" value="option1" checked>
								Option one is this and that&mdash;be sure to include why it's great
							</label>
						</div>
						<div class="form-check">
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios2" value="option2">
								Option two can be something else and selecting it will deselect option one
							</label>
						</div>
						<div class="form-check disabled">
							<label class="form-check-label">
								<input class="form-check-input" type="radio" name="gridRadios" id="gridRadios3" value="option3" disabled>
								Option three is disabled
							</label>
						</div>
					</div>
				</div>
			</fieldset>
			<div class="form-group row">
				<div class="col-sm-2">Checkbox</div>
				<div class="col-sm-10">
					<div class="form-check">
						<label class="form-check-label">
							<input class="form-check-input" type="checkbox"> Check me out
						</label>
					</div>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-10">
					<button type="submit" class="btn btn-primary">Sign in</button>
				</div>
			</div>-->
		</form>
	</div>
</template>

<script>
	import axios from 'axios';

	import auth from '../../auth';

	export default {
		data() {
			return {
				users: {}
			}
		},
		mounted() {
			axios.get('/users', { headers: auth.getAuthHeader() })
				.then((response) => {
					this.users = response.data;
				})
				.catch((error) => {
					console.log(error);
					auth.logout();
				});
		}
	}
</script>
