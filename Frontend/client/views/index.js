/**
 * @author : 최정우
 * @since : 2022.10.19
 * @dscription : Vue를 활용한 Client단 구현의 시작점(Index) Component 입니다.
 */
import { createApp } from "vue";

import AppRouter from "./pages/AppRouter.js";
import App from "./pages/App.vue";
import cmmnPlugin from './common/commonPlugin.js';



const app = createApp(App);
app.use(cmmnPlugin);
app.use(AppRouter);
app.mount("#root");

