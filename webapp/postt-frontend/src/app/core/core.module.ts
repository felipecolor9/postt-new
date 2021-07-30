import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { NavigationBarComponent } from "./component/nav-bar/nav-bar.component";

@NgModule({
    declarations: [NavigationBarComponent],
    imports: [RouterModule],
    exports: [NavigationBarComponent]
})
export class CoreModule {}