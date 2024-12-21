import User from "../models/User.js";
import express from "express";

const router = express.Router();

// Get dữ liệu
router.get("/", async (req, res) => {
  try {
    const users = await User.find({});
    res.json(users);
  } catch (error) {
    res.status(500).json({ error: "Error getting users", details: error });
  }
});
// Router login
router.post("/login", async (req, res) => {
  const { username, password } = req.body;
  try {
    const user = await User.findOne({ username: username });
    if (!user) return res.status(404).json({ message: "User not found" });
    if (user.password !== password)
      return res.status(401).json({ message: "Password incorrect" });
    res.json(user);
  } catch (error) {
    res.status(500).json({ error: "Error getting user", details: error });
  }
});
// Router đăng ký
router.post("/", async (req, res) => {
  const { username, password, email, education_level, gender, date_of_birth } =
    req.body;
  try {
    // Tạo người dùng mới
    const newUser = new User({
      username,
      password,
      email,
      education_level,
      gender,
      date_of_birth,
    });

    // Lưu người dùng vào MongoDB
    const savedUser = await newUser.save();
    res
      .status(201)
      .json({ message: "User registered successfully", user: savedUser });
  } catch (error) {
    res.status(500).json({ error: "Error registering user", details: error });
  }
});

// API cập nhật thông tin hồ sơ của người dùng
router.put("/updateProfile", async (req, res) => {
  const {
    username,
    email,
    gender,
    date_of_birth,
    education_level,
    avatar_img,
  } = req.body;

  if (!username) {
    return res.status(400).json({ message: "Thiếu thông tin username" });
  }

  try {
    // Tìm kiếm và cập nhật thông tin hồ sơ của người dùng
    const updatedUser = await User.findOneAndUpdate(
      { username },
      { email, gender, date_of_birth, education_level, avatar_img },
      { new: true }
    );

    if (!updatedUser) {
      return res.status(404).json({ message: "Người dùng không tồn tại" });
    }

    res.status(200).json({
      message: "Cập nhật thông tin hồ sơ thành công",
      user: updatedUser,
    });
  } catch (error) {
    res.status(500).json({
      message: "Có lỗi xảy ra khi cập nhật thông tin hồ sơ",
      details: error.message,
    });
  }
});
export default router;
