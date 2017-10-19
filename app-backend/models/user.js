//SCHEMA FOR USER ACCOUNTS

var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var bcrypt = require('bcrypt-nodejs');

var UserSchema = new Schema({
  name: String,
  type: String,
  username: { type: String, required: true, index: { unique: true }},
  password: { type: String, required: true, select: false }
});

//hash the password before the user is saved
UserSchema.pre('save', function(next) {
  var user = this;

  //hash the password only if the password has been changed
  if(!user.isModified('password')) return next();

  //generate the hash
  bcrypt.hash(user.password, null, null, function(err, hash) {
    if(err) return next(err);

    //change password to the hashed version
    user.password = hash;
    next();
  });
});

//compare given password with database hash
UserSchema.methods.comparePassword = function(password) {
  var user = this;

  return bcrypt.compareSync(password, user.password);
};

module.exports = mongoose.model('User', UserSchema);
