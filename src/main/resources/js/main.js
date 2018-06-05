import All, { initAll } from "@govuk-frontend/frontend/all";
import myModule from "./my-module";

All.initAll();

myModule();

const hi = () => {
   console.log('test');
}