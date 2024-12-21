import mongoose from "mongoose";

mongoose.connect('mongodb+srv://huynhbahuan:12345@cluster0.ywqlmv2.mongodb.net/db_kiemtracuoiky?retryWrites=true&w=majority&appName=Cluster0');

const db = mongoose.connection;

db.on('error', console.error.bind(console, 'MongoDB connection error:'));
db.once('open', () => {
  console.log('Mongoose connected to db');
});

export default db;