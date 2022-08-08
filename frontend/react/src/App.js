import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { Footer } from "./components/Footer";
import { Header } from "./components/Header";
import { LoginPage } from "./components/LoginPage";
import { MainPage } from "./components/MainPage";
import { Context } from "./components/Context";
import { useState, useEffect } from "react";
import axios from "axios";
import { Routes, Route } from "react-router-dom";
import { ForbittenEntrancePage } from "./components/ForbittenEntrancePage";

function App() {
  const [pageQty, setPageQty] = useState(0);
  const [certificates, setCertificates] = useState([]);
  const [query, setQuery] = useState("");
  const [page, setPage] = useState(1);
  const [forced, setForced] = useState(false);
  const [error, setError] = useState("");
  const [pageSize, setPageSize] = useState(5);

  const BASE_URL = "http://localhost:8085/module2/gift_certificates/";

  useEffect(() => {
    URL =
      BASE_URL +
      `filter/?sortByCreationDate=desc${query}&pageNumber=${
        page - 1
      }&pageSize=${pageSize}`;
    axios
      .get(URL)
      .then(({ data }) => {
        setCertificates(data.content);
        setPageQty(data.totalPages);
      })
      .catch((error) => {
        if (query.indexOf("partName") !== -1) {
          setQuery(query.replace("partName", "partDescription"));
        } else {
          editError(error);
        }
      });
  }, [query, page, forced]);

  const editPage = (page) => {
    setPage(page);
  };

  const editForced = (forced) => {
    setForced(forced);
  };

  const editQuery = (query) => {
    setQuery(query);
  };

  const editError = (error) => {
    setError(error);
  };

  const editPageSize = (pageSize) => {
    setPageSize(pageSize);
  };

  return (
    <div>
      <Context.Provider
        value={{
          editForced,
          editPage,
          editQuery,
          editError,
          editPageSize,
          error,
          page,
          forced,
          query,
          pageQty,
          certificates,
          pageSize,
        }}
      >
        <Header />
        <Routes>
          <Route path="login" element={<LoginPage />} />
          <Route path="certificates" element={<MainPage />} />
          <Route path="forbidden" element={<ForbittenEntrancePage />} />
        </Routes>
        <Footer />
      </Context.Provider>
    </div>
  );
}

export default App;
