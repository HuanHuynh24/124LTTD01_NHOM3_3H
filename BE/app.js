import express from "express";
import cors from "cors";
import useRouterUser from "./routers/User.js";


const app = express();
const port = 3000;

app.use(express.json());
app.use(
  cors({
    origin: "*",
  })
);

// Route cơ bản
app.get("/", (req, res) => {
  res.send(
    '<h1>xin chao</h1>'
  );
});

//  user routes
app.use("/api/users", useRouterUser);

// Lắng nghe cổng
app.listen(port, () => {
  console.log(`Server running on 3000`);
});
