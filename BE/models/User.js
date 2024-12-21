import mongoose from "mongoose";

const UserSchema = new mongoose.Schema({
  username: {
    type: String,
    required: true,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  education_level: {
    type: Number,
    required: true,
  },
  avatar_img: {
    type: String,
  },
  gender: {
    type: Boolean,
    required: true,
  },
  date_of_birth: {
    type: Date,
    required: true,
  },
});

export default mongoose.models.User || mongoose.model("user", UserSchema);
